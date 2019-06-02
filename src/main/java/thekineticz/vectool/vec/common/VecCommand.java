package thekineticz.vectool.vec.common;

/**
 * The base class for the internal representation of a line in a vec file.
 */
public abstract class VecCommand {

    private String commandName;

    /**
     * Creates a new VecCommand with a commandName
     *
     * @param commandName The string name of the command.
     */
    public VecCommand(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Abstract function for getting the arguments of a VecCommand as a string.
     *
     * @return The arguments of a VecCommand as a string.
     */
    public abstract String getArgs();

    /**
     * Gets the command name of the VecCommand.
     *
     * @return The command name of the VecCommand.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Gets the VecCommand in it's full string form.
     *
     * @return The VecCommand in it's full string form.
     */
    @Override
    public String toString() {
        return String.format("%s %s", commandName, getArgs());
    }
}
