package ch.rallo.mfgt.widget.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.rallo.mfgt.widget.R;
import ch.rallo.mfgt.widget.bean.ContactItem;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.contact);

		ListView listView = (ListView) findViewById(R.id.contactListView);
		ContactItemsAdapter contactItemsAdapter = new ContactItemsAdapter(getApplicationContext());

		for (ContactItem item : getItems()) {
			contactItemsAdapter.add(item);
		}

		listView.setAdapter(contactItemsAdapter);
	}

	private List<ContactItem> getItems() {
		List<ContactItem> items = new ArrayList<ContactItem>();

		String web = getString(R.string.contact_web);
		String mail = getString(R.string.contact_mail);
		String phone = getString(R.string.contact_phone);
		String address = getString(R.string.contact_address);

		items.add(new ContactItem(R.drawable.globe, web, openBrowser(web)));
		items.add(new ContactItem(R.drawable.envelope, mail, openMail(mail)));
		items.add(new ContactItem(R.drawable.phone, phone, doPhoneCall(phone)));
		items.add(new ContactItem(R.drawable.home, address));

		return items;
	}

	private static View.OnClickListener openBrowser(final String url) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, getUri(url));
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(browserIntent);
			}

			private Uri getUri(String url) {
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					url = "http://" + url;
				}
				return Uri.parse(url);
			}
		};
	}

	private static View.OnClickListener openMail(final String mailAddress) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailAddress});
				emailIntent.setType("message/rfc822");
				Intent chooser = Intent.createChooser(emailIntent, mailAddress);
				chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(chooser);
			}
		};
	}

	private static View.OnClickListener doPhoneCall(final String phoneNumber) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent phoneIntent = new Intent(Intent.ACTION_CALL);
				phoneIntent.setData(Uri.parse("tel:" + phoneNumber));
				phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(phoneIntent);
			}
		};
	}
}
