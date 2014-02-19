package codepath.apps.simpletodo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class EditItemActivity extends Activity {
	private EditText editItem;
	private String txt;
	private int pos;
	private final int WRONG_POS = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		editItem = (EditText) findViewById(R.id.editItem);

		txt = getIntent().getStringExtra("itemText");
		pos = getIntent().getIntExtra("pos", WRONG_POS);
		editItem.setText(txt);
			// Wire up button with event handler
		final Button button = (Button) findViewById(R.id.saveEditButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                String newText = (String) editItem.getText().toString();
                Intent nString = new Intent();
                nString.putExtra("newItem", newText);
                nString.putExtra("pos", pos);
                setResult(RESULT_OK, nString);
                finish();
			}
		});	editItem.setSelection(editItem.getText().length());
	}
}
