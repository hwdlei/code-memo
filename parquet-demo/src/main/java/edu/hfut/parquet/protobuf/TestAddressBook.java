package edu.hfut.parquet.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestAddressBook {
	private static byte[] encode(AddressBookProtos.AddressBook book) {
		return book.toByteArray();
	}

	private static AddressBookProtos.AddressBook decode(byte[] body) throws InvalidProtocolBufferException {
		return AddressBookProtos.AddressBook.parseFrom(body);
	}

	private static AddressBookProtos.AddressBook createAddressBook() {
		AddressBookProtos.AddressBook.Builder builder = AddressBookProtos.AddressBook.newBuilder();
		AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
		builder.addPerson(personBuilder.setId(123).setName("donglei").setEmail("asdfasdfasdf").build());
		return builder.build();
	}

	public static void main(String[] args) throws InvalidProtocolBufferException {
		AddressBookProtos.AddressBook book = createAddressBook();
		System.err.println("Before encode : " + book.toString());
		AddressBookProtos.AddressBook book2 = decode(encode(book));
		System.out.println("After encode : " + book2.toString());
		System.out.println("Assert equal : " + book.equals(book2));

	}

}
