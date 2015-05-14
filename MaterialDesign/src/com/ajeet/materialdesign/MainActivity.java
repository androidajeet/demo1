package com.ajeet.materialdesign;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajeet.materialdesign.controller.PostController;
import com.ajeet.materialdesign.model.Post;
import com.ajeet.materialdesign.util.Common;

public class MainActivity extends Activity {
	private RecyclerView mRecyclerView;
	private MaterialAdapter mAdapter;
	private PostController pc;

	private static final String MY_DB = "my_db";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country);
		SharedPreferences sp = getSharedPreferences(MY_DB, Context.MODE_PRIVATE);

		/**
		 * CHECK IF THIS IS USER'S FIRST VISIT
		 */
		boolean hasVisited = sp.getBoolean("hasVisited", false);
		
		if (!hasVisited) {
			
			PostController postController = new PostController(MainActivity.this);
			postController.addNewPost(new Post("At which lux level do the Nikon 1 cameras switch from PDAF to contrast-detection?", "Ajeet"));
			postController.addNewPost(new Post("How do I photograph a landscape with a solar eclipse where the sun is not the main subject?", "Abhishek"));
			postController.addNewPost(new Post("How can I adjust the colour temperature of an image programmatically?", "Mukesh kumar"));
			postController.addNewPost(new Post("Which portfolio hosting services offer swipe navigation on mobile devices?", "Ravi"));
			postController.addNewPost(new Post("Will an EMF AF Confirm M42 Lens To Canon EOS adapter actually confirm with the Helios 44-2 58mm f/2?", "Kushagra"));
			postController.addNewPost(new Post("What exactly is “base ISO” and how do I find what is base ISO on my camera?", "Devika Mehta"));
			postController.addNewPost(new Post("How can I light a staged showroom interior, where there is no ceiling to bounce light from?", "Aruna Yadav"));
			postController.addNewPost(new Post("Nikon D5100 “Press Shutter Release Button Again” error, but fixing itself after left alone for a while", "Vivek Mishra"));
			postController.addNewPost(new Post("How do I measure the correlated color temperature of a light source with a DSLR without a gray card?", "Neetu"));
			postController.addNewPost(new Post("How do I get a two person portrait with the background blurred using a DSLR and 50mm f/1.8 lens?", "Divya"));
			
			Editor e = sp.edit();
			e.putBoolean("hasVisited", true);
			e.commit();
		}

		setAdapter();
	}
	
	
	private void setAdapter()
	{
		pc = new PostController(MainActivity.this);
		List <Post> listPost = pc.getAllPost();
		mRecyclerView = (RecyclerView) findViewById(R.id.list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new MaterialAdapter(listPost, R.layout.row_country, this);
		mRecyclerView.setAdapter(mAdapter);
	}
	
	public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder>{

	    private List<Post> posts;
	    private int rowLayout;
	    private  Context mContext;
	    private int counter=0;
	    private int position=0;
	    Post p;
	    private PostController postController;
	    public MaterialAdapter(List<Post> posts, int rowLayout, Context context) {
	        this.posts = posts;
	        this.rowLayout = rowLayout;
	        this.mContext = context;
	       
	    }

	    @Override
	    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
	        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
	        return new ViewHolder(v);
	    }

	    @SuppressLint("NewApi") @Override
	    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
	        
	    	position=i;
	    	System.out.println("position::"+i);
	    	p= posts.get(i);
	        viewHolder.userName.setText(p.getPostedBy());
	        viewHolder.postString.setText( p.getPostText() );
	        viewHolder.createdDateString.setText(Common.getDifferenceBetweenDate( p.getCreatedDate(), Common. getCurrentLocalDateTime()));
	        viewHolder.noOfLikes.setText(p.getLikes()+"");
	        counter=p.getLikes();
	      
	    }

	    @Override
	    public int getItemCount() {
	        return posts == null ? 0 : posts.size();
	    }

	    public  class ViewHolder extends RecyclerView.ViewHolder {
	        public TextView userName, postString, createdDateString ,noOfLikes,likebutton;
	       // public Button likebutton;
	        public RelativeLayout countryImage;
	        public ViewHolder(View itemView) {
	            super(itemView);
	            userName = (TextView) itemView.findViewById(R.id.userName);
	            postString = (TextView) itemView.findViewById(R.id.dataTxt);
	            noOfLikes = (TextView) itemView.findViewById(R.id.likeCountTxt);
	            countryImage = (RelativeLayout)itemView.findViewById(R.id.letterImage);
	            createdDateString = (TextView) itemView.findViewById(R.id.timeStamp);
	            likebutton =(TextView) itemView.findViewById(R.id.like_icon);
	            noOfLikes.setTag(position);
	            likebutton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//int count = Integer.parseInt(noOfLikes.toString());
						counter++;
						PostController postController=new PostController(MainActivity.this);
						boolean isRightBuild=postController.updateTask(position,counter);
						if(isRightBuild)
						{
							p.setLikes(counter);
							v.setTag(p);
							mAdapter.notifyDataSetChanged();
							mRecyclerView.invalidate();
						}


					}
				});
	            
	            
	        }

	    }
	}

	
	



}
