package zx.soft.utils.json;

import java.util.Arrays;

public class User {
	public enum Gender {
		MALE, FEMALE
;
		public static Gender getG(String name) {

			return "MALE".equals(name) ? Gender.MALE : Gender.FEMALE;
		}
	};

	public static class Name {
		private String first, last;

		public String getFirst() {
			return first;
		}

		public String getLast() {
			return last;
		}

		public void setFirst(String s) {
			first = s;
		}

		public void setLast(String s) {
			last = s;
		}
	}

	private Gender gender;
	private Name name;
	private boolean isVerified;
	private byte[] userImage;

	public Name getName() {
		return name;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public Gender getGender() {
		return gender;
	}

	public byte[] getUserImage() {
		return userImage;
	}

	public void setName(Name n) {
		name = n;
	}

	public void setVerified(boolean b) {
		isVerified = b;
	}

	public void setGender(Gender g) {
		gender = g;
	}

	public void setUserImage(byte[] b) {
		userImage = b;
	}

	@Override
	public String toString() {
		return "User [_gender=" + gender + ", _name=[_first=" + name.first + ", _last=" + name.last
				+ "], _isVerified=" + isVerified + ", _userImage=" + Arrays.toString(userImage) + "]";
	}
}