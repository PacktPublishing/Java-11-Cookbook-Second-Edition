package com.packt.model;

import java.util.UUID;
import java.util.Date;
public class Comment{
	private UUID commentId = UUID.randomUUID();
	private String body;
	private User commentBy;
	private Date commentOn;
	private UUID postId;

	public Comment(){}
	public Comment(String body, User commentBy, UUID postId){
		this.body = body;
		this.commentBy = commentBy;
		this.commentOn = new Date();
		this.postId = postId;
	}

	public String getCommentId(){ return commentId.toString(); }

	public String getBody(){ return body; }
	public User getCommentBy(){ return commentBy; }
	public Date getCommentOn(){ return commentOn; }
	public String getPostId(){ return postId.toString(); }
}