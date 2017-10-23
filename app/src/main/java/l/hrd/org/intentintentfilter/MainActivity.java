package l.hrd.org.intentintentfilter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import l.hrd.org.intentintentfilter.entity.Contact;

public class MainActivity extends AppCompatActivity {

    private ListView contactList;
    private List<Contact> contacts;
    private Contact contact;
    private ContactAdapter adapter;
    private final int CONTACT_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        adapter=new ContactAdapter(this,contacts);
        contactList.setAdapter(adapter);
    }

    void initialize(){
        contactList = (ListView) findViewById(R.id.contact_list);
        contacts=new ArrayList();
    }

    public void onContactScreen(View view) {
        startActivityForResult(
                new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI),
                CONTACT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CONTACT_REQUEST_CODE:{
                if(resultCode==MainActivity.RESULT_OK){
                    Uri contactData=data.getData();
                    Cursor cursor=managedQuery(contactData,null,null,null,null);
                    if(cursor.moveToFirst()){
                        String id= cursor.getString(cursor.getColumnIndexOrThrow
                                        (ContactsContract.Contacts._ID));
                        String cNumber=null;
                        String hasPhone= cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        String name= cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));
                        if(hasPhone.equalsIgnoreCase("1")){
                            Cursor phone = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+id,
                                    null,
                                    null);

                            phone.moveToFirst();
                            cNumber=phone.getString(phone.getColumnIndex("data1"));
                        }
                        Log.v("Contact Name: ",name);
                        Log.v("Contact Number: ",cNumber);
                        contact=new Contact(name,cNumber);
                        contacts.add(contact);
                        adapter.notifyDataSetChanged();
                    }
                }

                break;
            }
        }
    }

    class ContactAdapter extends ArrayAdapter<Contact>{

        ContactAdapter(Context context ,List<Contact> list){
            super(context,0,list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Contact contact=getItem(position);
            if(null==convertView){
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.contact_item_layout,
                        parent,false);
            }
            TextView contactName= (TextView) convertView.findViewById(R.id.tv_contact_name);
            TextView contactNumber= (TextView) convertView.findViewById(R.id.tv_contact_number);
            contactName.setText(contact.getName());
            contactNumber.setText(contact.getPhoneNumber());
            return convertView;
        }
    }
}
