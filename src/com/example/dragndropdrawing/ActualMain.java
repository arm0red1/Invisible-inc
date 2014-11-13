package com.example.dragndropdrawing;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ActualMain extends Activity{
	listOfNotePads list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root_main);
		
		list = new listOfNotePads();
		Intent i = new Intent(list, list.getClass());
		startActivity(i);
		
		Button addNote = (Button)findViewById(R.id.addNotePadd);
		addNote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.createNote();
			}
		});
	}
}
