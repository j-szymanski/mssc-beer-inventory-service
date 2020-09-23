package prv.jws.brewery.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import prv.jws.brewery.model.BeerDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewInventoryEvent implements Serializable {

    static final long serialVersionUID = -4928064074737536983L;

    private BeerDto beerDto;
}
