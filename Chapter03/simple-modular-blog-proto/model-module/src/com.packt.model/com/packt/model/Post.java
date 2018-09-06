package com.packt.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.UUID;
import java.util.Collections;

public class Post{
	private UUID postId = UUID.randomUUID();
	private String title;
	private String body;
	private Set<String> tags = new HashSet<>();
	private Date postedOn;
	private User postedBy;
	private Date updatedOn;
	private User updatedBy;
	private List<Comment> comments = new ArrayList<>();

	public Post(){}
	public Post(String title, String body, Set<String> tags, User postedBy){
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.postedBy = postedBy;
		this.postedOn = new Date();
	}

	public String getPostId(){ return postId.toString(); }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title;}

	public String getBody(){ return body; }
	public void setBody(String body) { this.body = body; }

	public Set<String> getTags(){ 
		return Collections.unmodifiableSet(tags);
	}

	public void addTag(String tag){
		tags.add(tag);
	}

	public void removeTag(String tag){
		tags.remove(tag);
	}

	public User getPostedBy(){ return postedBy; }
	public Date getPostedOn(){ return postedOn; }

	public User getUpdatedBy(){ return updatedBy; }
	public void setUpdatedBy(User updatedBy){ this.updatedBy = updatedBy;}

	public Date getUpdatedOn(){ return updatedOn; }
	public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }

	public List<Comment> getComments(){
		return Collections.unmodifiableList(comments);
	}

	public void addComment(Comment comment){
		comments.add(comment);
	}

}