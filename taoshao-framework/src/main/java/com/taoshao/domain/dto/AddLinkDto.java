package com.taoshao.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author taoshao
 * @since 2024-06-05 10:59:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkDto {

    private String name;
    private String description;
    private String address;
    private String logo;
    private String status;

}

