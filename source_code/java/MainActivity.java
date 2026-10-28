import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalContacts, tvTotalFavorites;
    private ListView lvContacts;
    private Button btnAddContact;
    private ArrayList<Contact> contactList;
    private ContactAdapter adapter;

    public static final int REQUEST_ADD_CONTACT = 1;
    public static final int REQUEST_VIEW_CONTACT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalContacts = findViewById(R.id.tvTotalContacts);
        tvTotalFavorites = findViewById(R.id.tvTotalFavorites);
        lvContacts = findViewById(R.id.lvContacts);
        btnAddContact = findViewById(R.id.btnAddContact);

        initData();
        adapter = new ContactAdapter(this, contactList);
        lvContacts.setAdapter(adapter);

        updateStats();

        btnAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            ArrayList<String> phoneList = new ArrayList<>();
            for (Contact c : contactList) {
                phoneList.add(c.getPhone());
            }
            intent.putStringArrayListExtra("phone_list", phoneList);
            startActivityForResult(intent, REQUEST_ADD_CONTACT);
        });

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("contact", contactList.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_VIEW_CONTACT);
        });
    }

    private void initData() {
        contactList = new ArrayList<>();
        contactList.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, ""));
        contactList.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, ""));
        contactList.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Nam", false, ""));
    }

    private void updateStats() {
        tvTotalContacts.setText(String.valueOf(contactList.size()));
        int favoritesCount = 0;
        for (Contact c : contactList) {
            if (c.isFavorite()) favoritesCount++;
        }
        tvTotalFavorites.setText(String.valueOf(favoritesCount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD_CONTACT) {
                Contact newContact = (Contact) data.getSerializableExtra("new_contact");
                if (newContact != null) {
                    contactList.add(newContact);
                    adapter.notifyDataSetChanged();
                    updateStats();
                }
            } else if (requestCode == REQUEST_VIEW_CONTACT) {
                boolean isDeleted = data.getBooleanExtra("is_deleted", false);
                int position = data.getIntExtra("position", -1);
                if (isDeleted && position != -1) {
                    contactList.remove(position);
                    adapter.notifyDataSetChanged();
                    updateStats();
                }
            }
        }
    }
}