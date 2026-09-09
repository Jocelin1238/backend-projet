
package SystemeReservationticket.example.SystemeReservation.DTO;

import java.time.LocalDateTime;

import SystemeReservationticket.example.SystemeReservation.Model.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;

    private String message;

    private String type;

    private boolean lu;

    private LocalDateTime dateCreation;


    // =====================================================
    // CONVERSION ENTITY -> DTO
    // =====================================================

    public static NotificationDTO fromEntity(
            Notification notification) {

        return NotificationDTO.builder()

                .id(notification.getId())

                .message(notification.getMessage())

                .type(notification.getType())

                .lu(notification.isLu())

                .dateCreation(
                        notification.getDateCreation()
                )

                .build();
    }

}

