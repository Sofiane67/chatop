package fr.chatop.dto.response;

        import fr.chatop.dto.RentalDTO;
        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

        import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalsResponse {
    private Stream<RentalDTO> rentals;
}
