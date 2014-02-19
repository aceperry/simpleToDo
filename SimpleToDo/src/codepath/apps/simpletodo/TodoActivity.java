package codepath.apps.simpletodo;

import codepath.apps.simpletodo.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class TodoActivity extends Activity {
	private static final int WRONG_POS = -1;
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private int REQUEST_CODE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		readItems();	// populate the ArrayList (items), using file or blank if no file exists
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//TODO check request code for error
		if (resultCode == RESULT_OK) {
			String newText = data.getExtras().getString("newItem");
			int pos = data.getIntExtra("pos", WRONG_POS);
			//TODO check for pos error
			items.set(pos, newText);
            itemsAdapter.notifyDataSetChanged();
            saveItems();
		}
	}
	
	public void addTodoItem(View v) {
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {	// Long click listener on item
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
// Click listener on item
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> aView, View v, int pos,
					long id) {
				Intent eiIntent = new Intent(TodoActivity.this, EditItemActivity.class);
				eiIntent.putExtra("itemText", items.get(pos));
				eiIntent.putExtra("pos", pos);
				startActivityForResult(eiIntent, REQUEST_CODE);
			}
		});
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
