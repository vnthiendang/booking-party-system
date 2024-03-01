package com.swp.cms.dto;


import com.swp.entity.PService;
import com.swp.entity.Package;
import lombok.Data;

import java.util.List;

@Data
public class ListOrderDTO {
    public Package aPackage;
    public List<PService> pServicesList;
}
