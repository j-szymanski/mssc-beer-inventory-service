package prv.jws.brewery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable {

    static final long serialVersionUID = -6545729382714823740L;

    private UUID id;
    //    private Integer version;
    //    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = Shape.STRING)
    //    private OffsetDateTime createdDate;
    //    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = Shape.STRING)
    //    private OffsetDateTime lastModifiedDate;
    //
    private String beerName;
    private BeerStyle beerStyle;

    private String upc;

    //    @JsonFormat(shape = Shape.STRING)
    //    private BigDecimal price;

    private Integer quantityOnHand;
}


