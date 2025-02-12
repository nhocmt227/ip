package jessica;

import commands.UI;

public class Help {
    public static final String TODO_USAGE = "Usage: todo [description]";
    public static final String DEADLINE_USAGE = "Usage: deadline [description] /by [end date]";
    public static final String EVENT_USAGE = "Usage: event [description] /from [begin time] /to [end time]";
    public static final String DELETE_USAGE = "Usage: delete [index]";
    public static final String MARK_USAGE = "Usage: mark [index]";
    public static final String UNMARK_USAGE = "Usage: unmark [index]";
    public static final String LIST_USAGE = "Usage: list";
    public static final String FIND_USAGE = "Usage: find [message]";

    public static String help() {
        // for all static final attributes in Help, return
        String s = "Available commands: ";
        return UI.getPrettyArray(new String[] {s, TODO_USAGE, DEADLINE_USAGE, EVENT_USAGE, DELETE_USAGE, MARK_USAGE, UNMARK_USAGE, LIST_USAGE, FIND_USAGE});
    }
}
