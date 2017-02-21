package com.ez.sendem.function;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.ez.sendem.object.ContactData;

import java.io.InputStream;

public class ContactFunction {

    public static ContactData getPhoneContactInfo(Context context, String phoneNumber){
        ContactData contactData = new ContactData();

        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + phoneNumber + "'", null, null);

        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            String displayName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
            Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

            contactData.id = id;
            contactData.displayName = displayName;
            contactData.phoneNumber = phoneNumber;
            contactData.photoUri = photoUri;

            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, contactUri);
            if (input == null) {
                contactData.photo = null;
            } else {
                contactData.photo = BitmapFactory.decodeStream(input);
            }
        }
        c.close();
        return contactData;
    }
}
