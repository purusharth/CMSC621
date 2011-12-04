package SocialServer;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PublicProfile {
	public String gid="";
	public String name="";
	public Integer age=0;
	public ArrayList<String> hobbies = new ArrayList<String>();
	public String sex="";
	
	
	public String getGID() { return gid;}
	public String getName() { return name;}
	public Integer getAge() { return age;}
	public ArrayList<String> getHobbies() { return hobbies;}
	public String getSex() {return sex;}
	
	public void setGID(String gid) { this.gid = gid;}
	public void setName(String name) { this.name = name;}
	public void setAge(Integer age) { this.age = age;}
	public void setHobbies(ArrayList<String> hobbies) { this.hobbies = hobbies;}
	public void setSex(String sex) { this.sex = sex;}
	
	public void addHobby(String hobby){ hobbies.add(hobby);}
	public void removeHobby(String hobby){ hobbies.remove(hobby);}
	
	/*
	public String toString() {
        return String.format("gid=%s, name=%s, age=%d, sex=%s hobbies=[%s]", gid, name, age, sex, Arrays.toString(hobbies));
    }
    */
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

}
