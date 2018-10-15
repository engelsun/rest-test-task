package com.engelsun.resttesttask.exception;

import com.engelsun.resttesttask.dto.BusyParticipant;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class IllegalSelectedParticipantsException extends RuntimeException {
    public static final String MASSAGE = "The selected participants are working on others tasks during this period, please choose another period";

    private List<BusyParticipant> busyParticipants;

    public IllegalSelectedParticipantsException() {
        super(MASSAGE);
    }

    /**
     * If you will use this constructor, you should use method like this:

     private void processException(List<BusyParticipant> busyParticipants) {
         StringBuilder sb = new StringBuilder();
         sb.append("\n");
         sb.append(IllegalSelectedParticipantsException.MASSAGE);
         Collections.sort(busyParticipants);
         busyParticipants.forEach(participant -> {
             sb.append("\n");
             sb.append(participant.getName());
             sb.append(" busyFrom ");
             sb.append(participant.getBusyFrom());
             sb.append(" busyTo ");
             sb.append(participant.getBusyTo());
        });
        throw new IllegalSelectedParticipantsException(sb.toString());
     }

     This is necessary for the readability of the message in the console or
     for proper serialization in json to send to the client.
     * */
    public IllegalSelectedParticipantsException(String massage) {
        super(massage);
        this.busyParticipants = parseBusyParticipants(massage);
    }

    public List<BusyParticipant> parseBusyParticipants(String massage) {
        String[] split = massage.split("\n");
        List<BusyParticipant> busyParticipants = new ArrayList<>();
        BusyParticipant participant;
        for (int i = 2; i < split.length; i++) {
            String[] participantProperties = split[i].split(" ");
            participant = new BusyParticipant(
                    participantProperties[0].trim(),
                    LocalDate.parse(participantProperties[2]),
                    LocalDate.parse(participantProperties[4]));
            busyParticipants.add(participant);
        }
        return busyParticipants;
    }
}
