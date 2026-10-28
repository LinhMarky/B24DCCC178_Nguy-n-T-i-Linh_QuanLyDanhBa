package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    private EditText etName, etPhone, etNote;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;
    private Button btnSave;
    private ArrayList<String> existingPhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        spGroup = findViewById(R.id.spGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSave);

        String[] groups = {"Gia đình", "Bạn bè", "Công việc", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, groups);
        spGroup.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveContact());

        existingPhones = getIntent().getStringArrayListExtra("phone_list");
        if (existingPhones == null) {
            existingPhones = new ArrayList<>();
        }
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String group = spGroup.getSelectedItem().toString();
        String note = etNote.getText().toString().trim();
        boolean isFavorite = cbFavorite.isChecked();
        
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "Nam";
        if (selectedGenderId != -1) {
            RadioButton rbSelected = findViewById(selectedGenderId);
            gender = rbSelected.getText().toString();
        }

        if (TextUtils.isEmpty(name)) {
            etName.setError("Họ và tên không được để trống");
            return;
        }

        if (TextUtils.isEmpty(phone) || phone.length() != 10 || !phone.startsWith("0")) {
            etPhone.setError("Số điện thoại phải gồm 10 số và bắt đầu bằng 0");
            return;
        }

        if (existingPhones.contains(phone)) {
            etPhone.setError("Số điện thoại này đã tồn tại trong danh bạ");
            return;
        }

        Contact newContact = new Contact(name, phone, group, gender, isFavorite, note);
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_contact", newContact);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}