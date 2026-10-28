import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView tvAvatar, tvName, tvPhone, tvGroup, tvGender, tvFavorite, tvNote;
    private Button btnCall, btnBack, btnDelete;
    private Contact contact;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        tvAvatar = findViewById(R.id.tvAvatar);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvGroup = findViewById(R.id.tvGroup);
        tvGender = findViewById(R.id.tvGender);
        tvFavorite = findViewById(R.id.tvFavorite);
        tvNote = findViewById(R.id.tvNote);
        
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null) {
            contact = (Contact) intent.getSerializableExtra("contact");
            position = intent.getIntExtra("position", -1);
            
            if (contact != null) {
                displayContactDetails();
            }
        }

        btnCall.setOnClickListener(v -> {
            if (contact != null && contact.getPhone() != null) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
                startActivity(callIntent);
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("is_deleted", true);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void displayContactDetails() {
        String firstLetter = contact.getName() != null && !contact.getName().isEmpty() ? String.valueOf(contact.getName().charAt(0)).toUpperCase() : "";
        tvAvatar.setText(firstLetter);
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvGroup.setText("Nhóm: " + contact.getGroup());
        tvGender.setText("Giới tính: " + contact.getGender());
        
        if (contact.isFavorite()) {
            tvFavorite.setText("Yêu thích: Có");
            tvFavorite.setTextColor(0xFFFBC02D); // Vàng
        } else {
            tvFavorite.setText("Yêu thích: Không");
            tvFavorite.setTextColor(0xFF666666);
        }
        
        tvNote.setText("Ghi chú: " + (contact.getNote() != null ? contact.getNote() : ""));
    }
}