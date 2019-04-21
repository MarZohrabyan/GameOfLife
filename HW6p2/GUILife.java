import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GUILife extends JFrame implements ListSelectionListener {

    ArrayList<World> cachedWorlds = new ArrayList<>();
    private PatternStore store;
    private World world;
    protected JButton back, play, forward;

//    public GUILife() {
//        super("Game of Life");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(500, 400);
//        add(new JButton("Centre"));
//        add(new JButton("North"), BorderLayout.NORTH);
//        add(new JButton("South"), BorderLayout.SOUTH);
//        add(new JButton("West"), BorderLayout.WEST);
//        add(new JButton("East"), BorderLayout.EAST);
//    }

    public GUILife(PatternStore ps) {
        super("Game of Life");
        store = ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {

        GamePanel gamePanel = new GamePanel();
        addBorder(gamePanel,"Game Panel");
        try{
            gamePanel.display(new PackedWorld(store.getPatterns()[0]));
        }catch (Exception ex){

        }
        return gamePanel;
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        patt.setLayout(new BorderLayout());
        addBorder(patt, "Patterns");
        Pattern[] patterns = store.getPatterns();
        JList<Pattern> patternJList = new JList<>(patterns);
        patternJList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(patternJList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        patt.add(scrollPane, BorderLayout.CENTER);
        return patt;
    }

    private JPanel createControlPanel() {

        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
        back = new JButton("< Back");
        ctrl.add(back,BorderLayout.SOUTH);
        back.addActionListener(e -> moveBack());

        forward = new JButton("> Forward");
        ctrl.add(forward,BorderLayout.SOUTH);
        forward.addActionListener(e -> moveForward());

        play = new JButton("Play");

        ctrl.add(play,BorderLayout.SOUTH);
        return ctrl;
    }

    private World copyWorld(boolean useCloning) throws Exception {
        World copy = null;
        if (useCloning) {
            if (world instanceof ArrayWorld) {
                copy = new ArrayWorld((ArrayWorld) world);
            } else if (world instanceof PackedWorld) {
                copy = new PackedWorld((PackedWorld) world);
            } else {
                copy = world.clone();
            }

        }
        return copy;
    }

    public void moveBack() {
        String response = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if (response.startsWith("b")) {
            if (world.getGenerationCount() == 0) {
                return;
            } else if (world.getGenerationCount() > 0) {
                world = cachedWorlds.get(world.getGenerationCount() - 1);
                System.out.println();
            }
        }

    }

    public void moveForward(){

            String response = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                if (response.equals("f")) {
                    if (world == null) {
                        System.out.println("Please select a pattern to play (l to list):");

                    } else {
                        if (world.getGenerationCount() < cachedWorlds.size() - 1) {
                            world = cachedWorlds.get(world.getGenerationCount() + 1);
                        } else {
                            try {
                                cachedWorlds.add(copyWorld(true));
                                world.nextGeneration();

                                if (world instanceof ArrayWorld) {

                                    ((ArrayWorld) world).optimize();
                                }
                            }catch (Exception ex){
                            }
                        }

                    }
                }
    }
//    public  void checkButton() throws Exception{
//        JButton back = new JButton("< Back");
//        back.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//               moveBack();
//            }
//        });
//        add(back);
//        JButton forward = new JButton("Forward >");
//        forward.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                moveForward();
//            }
//        });
//        add(forward);
//    }




//    public void checkButton() throws Exception{
//        JButton back = new JButton("< Back");
//        back.addActionListener( e -> moveBack());
//        add(back);
//        JButton forward = new JButton("> Forward");
//        try {
//            forward.addActionListener(e -> moveForward());
//            add(forward);
//        }catch(Exception e){
//            throw new Exception("zzvacinq arden es eshutyunic");
//        }
//        if("back".equals(back.getActionCommand())){
//
//        }
//    }

    public static void main(String[] args) throws Exception {
        PatternStore ps = new PatternStore(args[0]);
        GUILife gui = new GUILife(ps);
        gui.setVisible(true);
        GamePanel gp = new GamePanel();
        PackedWorld pack = new PackedWorld(args[0]);
        gp.display(pack);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern p = list.getSelectedValue();
        World newWorld;
       if(p.getWidth()* p.getHeight()<= 64){
           newWorld = new PackedWorld((PackedWorld) world);
           world = newWorld;
       }
       else{
           try {
               newWorld = new ArrayWorld((ArrayWorld) world);
               world = newWorld;
           }catch (Exception ex){

           }
       }
    }
}

