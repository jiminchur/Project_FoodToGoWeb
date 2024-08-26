package com.foodtogo.mono.addresses.dto.response;

import com.foodtogo.mono.addresses.core.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private String address;
    private String request;

    public AddressResponseDto(Address address) {
        this.address = address.getAddress();
        this.request = address.getRequest();
    }
}
