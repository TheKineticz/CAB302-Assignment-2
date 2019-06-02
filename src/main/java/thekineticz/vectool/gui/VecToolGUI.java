package thekineticz.vectool.gui;

import thekineticz.vectool.VecTool;
import thekineticz.vectool.exception.*;
import thekineticz.vectool.vec.VecFile;
import thekineticz.vectool.vec.commands.*;
import thekineticz.vectool.vec.common.Position;
import thekineticz.vectool.vec.common.VecCommand;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * VecTool GUI main class.
 */
public class VecToolGUI extends JFrame {

    private static final String TITLE = "VECtor Design Tool";
    private static final String DEFAULT_FILENAME = "untitled";
    private static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter("VEC File (*.vec)", VecFile.FILE_EXTENSION);
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1000, 800);

    private VecToolGUIMenuBar menuBar;
    private VecToolbar toolbar;
    private JPanel canvasPanel;
    private VecCanvas vecCanvas;

    private VecFile vecFile = null;

    /**
     * Creates and shows the VecTool GUI.
     * For safety, this should be invoked from the event-dispatching thread.
     */
    public VecToolGUI() {
        new VecTool();

        //Set up frame
        setPreferredSize(DEFAULT_WINDOW_SIZE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(TITLE);

        //Set up menu bar
        menuBar = new VecToolGUIMenuBar();
        setJMenuBar(menuBar);

        //Set up tool bar
        toolbar = new VecToolbar();
        add(toolbar, BorderLayout.NORTH);

        //Set up canvas
        canvasPanel = new JPanel(new GridBagLayout());
        canvasPanel.setBackground(Color.LIGHT_GRAY);
        add(canvasPanel, BorderLayout.CENTER);

        //Override window close operation to custom function.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGUI();
            }
        });

        pack();
        setVisible(true);
    }

    /**
     * Removes an existing canvas from the canvas panel.
     */
    private void removeCanvas(){
        canvasPanel.remove(vecCanvas);
        canvasPanel.revalidate();
        canvasPanel.repaint();
    }

    /**
     * Creates a new canvas tied to a VecFile.
     *
     * @param vecFile The VecFile to draw on the canvas.
     */
    private void createCanvas(VecFile vecFile){
        VecCanvasEditor editor = new VecCanvasEditor();

        vecCanvas = new VecCanvas(vecFile, editor);
        vecCanvas.addMouseListener(editor);
        vecCanvas.addMouseMotionListener(editor);
        canvasPanel.add(vecCanvas);
        canvasPanel.revalidate();
    }

    /**
     * Create a dialog prompting the user to save.
     *
     * @return Whether the process can proceed.
     */
    private boolean promptToSave(){
        Object[] options = {"Save", "Don't Save", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                String.format("Do you want to save changes to %s?", vecFile.getFilename()),
                TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
        );

        //Ready to proceed if file successfully saves or user chooses not to save.
        if (choice == JOptionPane.OK_OPTION){
            return vecFile.isNewFile() ? saveVecFileAs() : saveVecFile();
        }
        else return choice != JOptionPane.CANCEL_OPTION;
    }

    /**
     * Prompt to save existing file, the deregister the GUI instance and close the application.
     */
    private void exitGUI(){
        //Intercept the window close operation if we have an unsaved file.
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        VecTool.close();
        System.exit(0);
    }

    /**
     * Saves the VecFile to it's original file.
     *
     * @return Whether the operation was successful.
     */
    private boolean saveVecFile(){
        try {
            vecFile.save();
            return true;
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while trying to save the file.\nPlease save to a new location.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            saveVecFileAs();
        }

        return false;
    }

    /**
     * Saves the VecFile to a new file.
     *
     * @return Whether the operation was successful.
     */
    private boolean saveVecFileAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setSelectedFile(new File(String.format("%s.%s", vecFile.getFilename(), VecFile.FILE_EXTENSION)));
        fileChooser.setFileFilter(FILE_FILTER);

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            try {
                vecFile.saveAs(file);
                setTitle(String.format("%s - %s", vecFile.getFilename(), TITLE));
                menuBar.saveFileButton.setEnabled(true);
                return true;
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(
                        this,
                        "IO Error while writing to file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }

        return false;
    }

    /**
     * Prompt to save existing file and create a new VecFile.
     */
    private void createNewVecFile(){
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        if (vecCanvas != null){
            removeCanvas();
        }

        vecFile = new VecFile(DEFAULT_FILENAME);
        setTitle(String.format("%s - %s", vecFile.getFilename(), TITLE));
        menuBar.closeFileButton.setEnabled(true);
        menuBar.saveFileButton.setEnabled(false);
        menuBar.saveAsFileButton.setEnabled(true);
        menuBar.undoLastButton.setEnabled(!vecFile.getCommands().isEmpty());
        toolbar.colourSelector.reset();

        createCanvas(vecFile);
    }

    /**
     * Prompt to save existing file and start prompt to open an existing vec file.
     */
    private void openVecFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileFilter(FILE_FILTER);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            if (vecFile != null && !vecFile.isSaved()){
                if (!promptToSave()){
                    return;
                }
            }

            if (vecCanvas != null){
                removeCanvas();
            }

            try {
                vecFile = new VecFile(file);
                setTitle(String.format("%s - %s", vecFile.getFilename(), TITLE));

                menuBar.closeFileButton.setEnabled(true);
                menuBar.saveFileButton.setEnabled(true);
                menuBar.saveAsFileButton.setEnabled(true);
                menuBar.undoLastButton.setEnabled(!vecFile.getCommands().isEmpty());
                toolbar.colourSelector.setPenColour(ColourHexConverter.hex2rgb(vecFile.getLatestPenColour()));
                toolbar.colourSelector.setFillColour(ColourHexConverter.hex2rgb(vecFile.getLatestFillColour()));

                createCanvas(vecFile);
            }
            catch (VecCommandException e){
                JOptionPane.showMessageDialog(
                        this,
                        String.format("Invalid line in file.\nError: %s", e.getMessage()),
                        "Error in file",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(
                        this,
                        "IO Error while reading from file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Prompt to save existing file and close file.
     */
    private void closeVecFile(){
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        if (vecCanvas != null){
            removeCanvas();
        }

        vecFile = null;
        setTitle(TITLE);
        menuBar.closeFileButton.setEnabled(false);
        menuBar.saveFileButton.setEnabled(false);
        menuBar.saveAsFileButton.setEnabled(false);
        menuBar.undoLastButton.setEnabled(false);
        toolbar.colourSelector.reset();
    }

    /**
     * Inner class for the menu bar of the VecToolGUI.
     */
    private class VecToolGUIMenuBar extends JMenuBar implements ActionListener {

        JMenu fileMenu;
        JMenuItem newFileButton;
        JMenuItem openFileButton;
        JMenuItem closeFileButton;
        JMenuItem saveFileButton;
        JMenuItem saveAsFileButton;
        JMenuItem exitButton;

        JMenu editMenu;
        JMenuItem undoLastButton;

        /**
         * Creates a new menu bar.
         */
        VecToolGUIMenuBar(){
            //Setup File menu
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            newFileButton = new JMenuItem("New");
            newFileButton.setMnemonic(KeyEvent.VK_N);
            newFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
            newFileButton.addActionListener(this );

            openFileButton = new JMenuItem("Open");
            openFileButton.setMnemonic(KeyEvent.VK_O);
            openFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
            openFileButton.addActionListener(this );

            closeFileButton = new JMenuItem("Close File");
            closeFileButton.setMnemonic(KeyEvent.VK_C);
            closeFileButton.addActionListener(this );
            closeFileButton.setEnabled(false);

            saveFileButton = new JMenuItem("Save");
            saveFileButton.setMnemonic(KeyEvent.VK_S);
            saveFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
            saveFileButton.addActionListener(this);
            saveFileButton.setEnabled(false);

            saveAsFileButton = new JMenuItem("Save As");
            saveAsFileButton.setMnemonic(KeyEvent.VK_A);
            saveAsFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
            saveAsFileButton.addActionListener(this);
            saveAsFileButton.setEnabled(false);

            exitButton = new JMenuItem("Exit");
            exitButton.setMnemonic(KeyEvent.VK_X);
            exitButton.addActionListener(this );

            fileMenu.add(newFileButton);
            fileMenu.add(openFileButton);
            fileMenu.add(closeFileButton);
            fileMenu.add(new JSeparator());
            fileMenu.add(saveFileButton);
            fileMenu.add(saveAsFileButton);
            fileMenu.add(new JSeparator());
            fileMenu.add(exitButton);

            //Setup Edit menu
            editMenu = new JMenu("Edit");
            editMenu.setMnemonic(KeyEvent.VK_E);

            undoLastButton = new JMenuItem("Undo latest command");
            undoLastButton.setMnemonic(KeyEvent.VK_U);
            undoLastButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
            undoLastButton.addActionListener(this);
            undoLastButton.setEnabled(false);

            editMenu.add(undoLastButton);

            //Setup menu bar
            add(fileMenu); add(editMenu);
        }

        /**
         * Event handler for the menu bar.
         * Executes button actions.
         *
         * @param event The event that occurred.
         */
        public void actionPerformed(ActionEvent event){
            if (event.getSource() == newFileButton){
                createNewVecFile();
            }
            else if (event.getSource() == openFileButton){
                openVecFile();
            }
            else if (event.getSource() == closeFileButton){
                closeVecFile();
            }
            else if (event.getSource() == saveFileButton){
                saveVecFile();
            }
            else if (event.getSource() == saveAsFileButton){
                saveVecFileAs();
            }
            else if (event.getSource() == undoLastButton){
                vecFile.undoLatestCommand();
                vecCanvas.repaint();
                undoLastButton.setEnabled(!vecFile.getCommands().isEmpty());
            }
            else if (event.getSource() == exitButton){
                exitGUI();
            }
        }
    }

    /**
     * Listener class for editing a VecCanvas.
     */
    class VecCanvasEditor implements MouseListener, MouseMotionListener {

        private Class previousTool;
        private Class activeTool;
        private Position mousePosition;
        private static final int SNAP_THRESHOLD = 10;

        private ArrayList<Position> positionBuffer;

        VecCanvasEditor(){
            previousTool = null;
            positionBuffer = new ArrayList<>();
        }

        Class getActiveTool() { return activeTool; }
        ArrayList<Position> getPositionBuffer(){ return positionBuffer; }
        Position getMousePosition(){ return mousePosition; }
        Color getNextPenColour(){ return toolbar.colourSelector.getPenColour(); }
        Color getNextFillColour(){ return toolbar.colourSelector.getFillColour(); }

        private void addPlot(Position position){
            try {
                vecFile.addCommand(new PenCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getPenColour())));
                vecFile.addCommand(new PlotCommand(position));
            }
            catch (VecCommandException e){
                e.printStackTrace();
            }

            vecCanvas.repaint();
        }

        private void addLine(){
            try {
                vecFile.addCommand(new PenCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getPenColour())));
                vecFile.addCommand(new LineCommand((ArrayList)positionBuffer.clone()));
            }
            catch (VecCommandException e){
                e.printStackTrace();
            }

            vecCanvas.repaint();
        }

        private void addRectangle(){
            try {
                vecFile.addCommand(new PenCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getPenColour())));
                vecFile.addCommand(new FillCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getFillColour())));
                vecFile.addCommand(new RectangleCommand((ArrayList)positionBuffer.clone()));
            }
            catch (VecCommandException e){
                e.printStackTrace();
            }

            vecCanvas.repaint();
        }

        private void addEllipse(){
            try {
                vecFile.addCommand(new PenCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getPenColour())));
                vecFile.addCommand(new FillCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getFillColour())));
                vecFile.addCommand(new EllipseCommand((ArrayList)positionBuffer.clone()));
            }
            catch (VecCommandException e){
                e.printStackTrace();
            }

            vecCanvas.repaint();
        }

        private void addPolygon(){
            try {
                vecFile.addCommand(new PenCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getPenColour())));
                vecFile.addCommand(new FillCommand(ColourHexConverter.rgb2hex(toolbar.colourSelector.getFillColour())));
                vecFile.addCommand(new PolygonCommand((ArrayList)positionBuffer.clone()));
            }
            catch (VecCommandException e){
                e.printStackTrace();
            }

            vecCanvas.repaint();
        }

        @Override
        public void mousePressed(MouseEvent event){
            if (previousTool != null && !previousTool.equals(activeTool)){
                positionBuffer.clear();
            }

            Position eventPosition = new Position((double)event.getX() / vecCanvas.getWidth(), (double)event.getY() / vecCanvas.getHeight());

            if (toolbar.toolSelector.plotToolButton.isSelected()){
                previousTool = PlotCommand.class;
                activeTool = PlotCommand.class;
                addPlot(eventPosition);
            }
            else if (toolbar.toolSelector.lineToolButton.isSelected()) {
                activeTool = LineCommand.class;
                positionBuffer.add(eventPosition);
            }
            else if (toolbar.toolSelector.rectangleToolButton.isSelected()) {
                activeTool = RectangleCommand.class;
                positionBuffer.add(eventPosition);
            }
            else if (toolbar.toolSelector.ellipseToolButton.isSelected()) {
                activeTool = EllipseCommand.class;
                positionBuffer.add(eventPosition);
            }
            else if (toolbar.toolSelector.polygonToolButton.isSelected()){
                activeTool = PolygonCommand.class;
                if (!positionBuffer.isEmpty() && eventPosition.getDistance(positionBuffer.get(0)) < (double)SNAP_THRESHOLD / getWidth()){
                    previousTool = PolygonCommand.class;
                    addPolygon();
                    positionBuffer.clear();
                }
                else {
                    activeTool = PolygonCommand.class;
                    previousTool = PolygonCommand.class;
                    positionBuffer.add(eventPosition);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent event){
            Position eventPosition = new Position((double)event.getX() / vecCanvas.getWidth(), (double)event.getY() / vecCanvas.getHeight());

            if (toolbar.toolSelector.lineToolButton.isSelected() && positionBuffer.size() == 1) {
                previousTool = LineCommand.class;
                positionBuffer.add(eventPosition);
                addLine();
                positionBuffer.clear();
            }
            else if (toolbar.toolSelector.rectangleToolButton.isSelected() && positionBuffer.size() == 1) {
                previousTool = RectangleCommand.class;
                positionBuffer.add(eventPosition);
                addRectangle();
                positionBuffer.clear();
            }
            else if (toolbar.toolSelector.ellipseToolButton.isSelected() && positionBuffer.size() == 1) {
                previousTool = EllipseCommand.class;
                positionBuffer.add(eventPosition);
                addEllipse();
                positionBuffer.clear();
            }
        }

        @Override
        public void mouseDragged(MouseEvent event){
            mousePosition = new Position((double)event.getX() / vecCanvas.getWidth(),(double)event.getY() / vecCanvas.getHeight());

            if (toolbar.toolSelector.plotToolButton.isSelected()){
                activeTool = PlotCommand.class;
                addPlot(mousePosition);
                vecCanvas.repaint();
            }
            else if (toolbar.toolSelector.lineToolButton.isSelected()){
                activeTool = LineCommand.class;
                vecCanvas.repaint();
            }
            else if (toolbar.toolSelector.rectangleToolButton.isSelected()){
                activeTool = RectangleCommand.class;
                vecCanvas.repaint();
            }
            else if (toolbar.toolSelector.ellipseToolButton.isSelected()){
                activeTool = EllipseCommand.class;
                vecCanvas.repaint();
            }
            else if (toolbar.toolSelector.polygonToolButton.isSelected() && !positionBuffer.isEmpty()){
                activeTool = PolygonCommand.class;
                vecCanvas.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event){
            mousePosition = new Position((double)event.getX() / vecCanvas.getWidth(),(double)event.getY() / vecCanvas.getHeight());

            if (toolbar.toolSelector.polygonToolButton.isSelected() && !positionBuffer.isEmpty()){
                activeTool = PolygonCommand.class;
                vecCanvas.repaint();
            }
        }

        //Unused interface functions
        @Override
        public void mouseEntered(MouseEvent event){}

        @Override
        public void mouseExited(MouseEvent event){}

        @Override
        public void mouseClicked(MouseEvent event){}
    }
}
