package CoreTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

import edu.umd.cloud9.io.map.HashMapWritable;

public class Users implements Writable {

	private HashMapWritable<IntWritable, User> users = new HashMapWritable<IntWritable, User>();

	public Users() {
	}

	@Override
	public String toString() {
		Iterator<IntWritable> keyUsers = users.keySet().iterator();
		StringBuilder bld = new StringBuilder();
		while (keyUsers.hasNext())
			bld.append(keyUsers.next().toString() + ", ");
		return bld.toString();
	}

	public void addUser(User user) {
		users.put(new IntWritable(user.getID()), user);
	}

	public User getUser(int id) { // maybe muggy .. add intWritable
		return users.get(id);
	}

	public ArrayList<User> getUserList() {
		return (ArrayList) users.values();
	}

	public Iterator<User> iterator() {
		return users.values().iterator();
	}

	public void write(DataOutput out) throws IOException {
		users.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		users.readFields(in);
	}
}
