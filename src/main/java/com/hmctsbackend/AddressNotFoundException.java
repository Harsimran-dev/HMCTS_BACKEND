package com.hmctsbackend;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long addressId) {
        super("Address not found with id: " + addressId);
    }
}
