package com.packt.processor;


public class Student{
	public String gender;
	public int age;
	public ParentEducation motherEducation;
	public ParentEducation fatherEducation;
	public String motherJob;
	public String fatherJob;
	public boolean tookPaidTution;
	public int firstTermGrade;
	public int secondTermGrade;
	public int finalGrade;

	public Student(String[] elements){
		//"F";18;4;4;"at_home";"teacher";"no";"5";"6";6
		this.gender = elements[0];
		this.age = Integer.parseInt(elements[1]);
		this.motherEducation = ParentEducation.values()[
			Integer.parseInt(elements[2])];
		this.fatherEducation = ParentEducation.values()[
			Integer.parseInt(elements[3])];
		this.motherJob = elements[4];
		this.fatherJob = elements[5];

		this.tookPaidTution = "yes".equals(elements[6])?true : false;
		this.firstTermGrade = Integer.parseInt((elements[7]).substring(1,2));
		this.secondTermGrade = Integer.parseInt((elements[8]).substring	(1,2));
		this.finalGrade = Integer.parseInt(elements[9]);
	} 

	public String getGender() { return gender; }
	
	public int getAge(){ return age; }
	public ParentEducation getMotherEducation(){ return motherEducation; }
	public ParentEducation getFatherEducation(){ return fatherEducation; }
	public String getMotherJob(){ return motherJob; }
	public String getFatherJob(){ return fatherJob; }
	public boolean isTookPaidTution() { return tookPaidTution;}
	public int getFirstTermGrade(){ return firstTermGrade;}
	public int getSecondTermGrade(){ return secondTermGrade;}
	public int getFinalGrade(){ return finalGrade;}
}