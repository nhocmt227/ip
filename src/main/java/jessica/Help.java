package jessica;

import commands.UI;

public class Help {

    public static final String LIST_USAGE = "> list: See all the tasks";
    public static final String TODO_USAGE = "> todo [description]";
    public static final String DEADLINE_USAGE = "> deadline [description] /by [end date]";
    public static final String EVENT_USAGE = "> event [description] /from [start time] /to [end time]";
    public static final String DELETE_USAGE = "> delete [task number]";
    public static final String MARK_USAGE = "> mark [task number]";
    public static final String UNMARK_USAGE = "> unmark [task number]";
    public static final String FIND_USAGE = "> find [keyword]";
    public static final String BYE_USAGE = "> bye";


    public static String help() {
        String[] commands = {
                "Available Commands:",
                LIST_USAGE,
                TODO_USAGE,
                DEADLINE_USAGE,
                EVENT_USAGE,
                DELETE_USAGE,
                MARK_USAGE,
                UNMARK_USAGE,
                FIND_USAGE,
                BYE_USAGE
        };
        return UI.getPrettyArray(commands);
    }

    public static String chatbotHello() {
        return "👋 Hello! I'm Jessica, your personal task manager.\n"
                + "\n💡 Tip: Type 'help' anytime to see the available commands!";
    }
}
