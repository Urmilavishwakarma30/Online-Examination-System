package com.onlineexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class OnlineExamSystem extends JFrame {
	
	private JButton previousButton;
	private JButton nextButton;
	private JButton submitButton;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private User currentUser;
    private final User registeredUser = new User("admin", "1234", "Admin");

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private String[] selectedAnswers;

    private JLabel questionNumberLabel;
    private JLabel questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionGroup;

    private JLabel timerLabel;
    private javax.swing.Timer examTimer;
    private int remainingSeconds = 30 * 60;
    private boolean examInProgress = false;

    public OnlineExamSystem() {
        setTitle("Online Examination System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (examInProgress) {
                    int choice = JOptionPane.showConfirmDialog(
                            OnlineExamSystem.this,
                            "The exam is still in progress. Are you sure you want to close?",
                            "Exit Exam",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (choice == JOptionPane.YES_OPTION) {
                        stopExamTimer();
                        examInProgress = false;
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });
        showLoginScreen();
    }

 // ================= LOGIN SCREEN =================

 // ================= LOGIN SCREEN =================

    private void showLoginScreen() {

        getContentPane().removeAll();

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 244, 255));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        );

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "ONLINE EXAMINATION SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setForeground(
                new Color(40, 70, 150)
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // ================= LOGIN CARD =================

        JPanel loginPanel = new JPanel(
                new GridBagLayout()
        );

        loginPanel.setBackground(Color.WHITE);

        loginPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(80, 110, 200),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(12, 12, 12, 12);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // ================= LOGIN HEADING =================

        JLabel loginHeading = new JLabel(
                "Student Login",
                SwingConstants.CENTER
        );

        loginHeading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        loginHeading.setForeground(
                new Color(50, 80, 160)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        loginPanel.add(
                loginHeading,
                gbc
        );

        // ================= USERNAME =================

        JLabel usernameLabel =
                new JLabel("Username:");

        usernameLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        usernameLabel.setForeground(
                new Color(60, 60, 60)
        );

        usernameField =
                new JTextField(20);

        // ================= PASSWORD =================

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        passwordLabel.setForeground(
                new Color(60, 60, 60)
        );

        passwordField =
                new JPasswordField(20);

        // ================= USERNAME ROW =================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        loginPanel.add(
                usernameLabel,
                gbc
        );

        gbc.gridx = 1;

        loginPanel.add(
                usernameField,
                gbc
        );

        // ================= PASSWORD ROW =================

        gbc.gridx = 0;
        gbc.gridy = 2;

        loginPanel.add(
                passwordLabel,
                gbc
        );

        gbc.gridx = 1;

        loginPanel.add(
                passwordField,
                gbc
        );

        // ================= LOGIN BUTTON =================

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        loginButton.setForeground(Color.WHITE);

        loginButton.setBackground(
                new Color(50, 100, 200)
        );

        loginButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        loginPanel.add(
                loginButton,
                gbc
        );

        // ================= ADD CARD =================

        mainPanel.add(
                loginPanel,
                BorderLayout.CENTER
        );

        // ================= INFO =================

        JLabel infoLabel = new JLabel(
                "Demo Login:  admin  /  1234",
                SwingConstants.CENTER
        );

        infoLabel.setFont(
                new Font("Arial", Font.ITALIC, 13)
        );

        infoLabel.setForeground(
                new Color(90, 90, 90)
        );

        mainPanel.add(
                infoLabel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // ================= BUTTON ACTION =================

        loginButton.addActionListener(
                e -> login()
        );

        passwordField.addActionListener(
                e -> login()
        );

        revalidate();
        repaint();
    }
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.equals(registeredUser.getUsername()) && password.equals(registeredUser.getPassword())) {
            currentUser = registeredUser;
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDashboard() {

        getContentPane().removeAll();

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(238, 243, 255));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        // ================= HEADER =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 75, 160));
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel titleLabel = new JLabel("ONLINE EXAMINATION SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + registeredUser.getDisplayName()
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(220, 230, 255));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(welcomeLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= CENTER =================
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(238, 243, 255));

        JPanel dashboardCard = new JPanel();
        dashboardCard.setLayout(new BoxLayout(dashboardCard, BoxLayout.Y_AXIS));
        dashboardCard.setBackground(Color.WHITE);
        dashboardCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(100, 130, 210), 2
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 45, 30, 45
                        )
                )
        );

        JLabel dashboardTitle = new JLabel(
                "Student Dashboard",
                SwingConstants.CENTER
        );
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 25));
        dashboardTitle.setForeground(new Color(45, 75, 160));
        dashboardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel(
                "Choose an option to continue",
                SwingConstants.CENTER
        );
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(90, 90, 90));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashboardCard.add(dashboardTitle);
        dashboardCard.add(Box.createVerticalStrut(8));
        dashboardCard.add(instructionLabel);
        dashboardCard.add(Box.createVerticalStrut(25));

        // ================= START EXAM BUTTON =================
        JButton startExamButton = new JButton("START EXAM");
        startExamButton.setFont(new Font("Arial", Font.BOLD, 17));
        startExamButton.setForeground(Color.WHITE);
        startExamButton.setBackground(new Color(40, 160, 100));
        startExamButton.setFocusPainted(false);
        startExamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startExamButton.setPreferredSize(new Dimension(260, 45));
        startExamButton.setMaximumSize(new Dimension(260, 45));

        // ================= PROFILE BUTTON =================
        JButton profileButton = new JButton("UPDATE PROFILE");
        profileButton.setFont(new Font("Arial", Font.BOLD, 16));
        profileButton.setForeground(Color.WHITE);
        profileButton.setBackground(new Color(70, 110, 200));
        profileButton.setFocusPainted(false);
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setPreferredSize(new Dimension(260, 45));
        profileButton.setMaximumSize(new Dimension(260, 45));

        // ================= LOGOUT BUTTON =================
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(210, 70, 70));
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setPreferredSize(new Dimension(260, 45));
        logoutButton.setMaximumSize(new Dimension(260, 45));

        dashboardCard.add(startExamButton);
        dashboardCard.add(Box.createVerticalStrut(15));
        dashboardCard.add(profileButton);
        dashboardCard.add(Box.createVerticalStrut(15));
        dashboardCard.add(logoutButton);

        centerPanel.add(dashboardCard);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ================= FOOTER =================
        JLabel footerLabel = new JLabel(
                "Online Examination System | Java Swing",
                SwingConstants.CENTER
        );
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        footerLabel.setForeground(new Color(80, 80, 80));

        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // ================= BUTTON ACTIONS =================

        startExamButton.addActionListener(e -> startExam());

        profileButton.addActionListener(e -> showProfileScreen());

        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                showLoginScreen();
            }
        });

        revalidate();
        repaint();
    }

    private void showProfileScreen() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        JLabel titleLabel = new JLabel("PROFILE UPDATE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField usernameTextField = new JTextField(currentUser.getUsername(), 20);
        usernameTextField.setEditable(false);
        JTextField displayNameField = new JTextField(currentUser.getDisplayName(), 20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JButton saveButton = new JButton("Save Profile");
        JButton cancelButton = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; formPanel.add(usernameTextField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Display Name:"), gbc);
        gbc.gridx = 1; formPanel.add(displayNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1; formPanel.add(newPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(saveButton, gbc);
        gbc.gridx = 1; formPanel.add(cancelButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        saveButton.addActionListener(e -> {
            String displayName = displayNameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword());
            if (displayName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Display name cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            currentUser.setDisplayName(displayName);
            currentUser.setPassword(newPassword);
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showDashboard();
        });
        cancelButton.addActionListener(e -> showDashboard());
        revalidate();
        repaint();
    }

    private void startExam() {

        createQuestions();

        currentQuestionIndex = 0;

        selectedAnswers = new String[questions.size()];

        remainingSeconds = 30 * 60;

        examInProgress = true;

        showExamScreen();

        startExamTimer();
    }

    private void createQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Which keyword is used to create a class in Java?", "class", "struct", "define", "object", "A"));
        questions.add(new Question("Which method is the entry point of a Java program?", "start()", "main()", "run()", "execute()", "B"));
        questions.add(new Question("Which keyword is used to inherit a class?", "implements", "inherits", "extends", "super", "C"));
        questions.add(new Question("Which data type stores true or false?", "int", "boolean", "String", "double", "B"));
        questions.add(new Question("Which symbol is used to end a Java statement?", ".", ":", ";", ",", "C"));
        questions.add(new Question("Which collection allows duplicate elements?", "Set", "Map", "List", "None", "C"));
        questions.add(new Question("Which keyword is used to create an object?", "new", "create", "object", "make", "A"));
        questions.add(new Question("Which keyword is used for exception handling?", "try", "check", "error", "handle", "A"));
        questions.add(new Question("Which class is commonly used to read console input?", "Reader", "Scanner", "Input", "ConsoleReader", "B"));
        questions.add(new Question("Which concept allows one interface to have multiple implementations?", "Inheritance", "Polymorphism", "Encapsulation", "Compilation", "B"));
    }
    
    
    
    private void showExamScreen() {

        getContentPane().removeAll();

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(238, 243, 255));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // ================= HEADER =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 75, 160));
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JLabel examTitle = new JLabel("JAVA ONLINE EXAM");
        examTitle.setFont(new Font("Arial", Font.BOLD, 24));
        examTitle.setForeground(Color.WHITE);

        timerLabel = new JLabel("Time Remaining: 30:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setForeground(new Color(255, 235, 100));

        headerPanel.add(examTitle, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ================= QUESTION CARD =================
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBackground(Color.WHITE);

        questionPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(100, 130, 210), 2
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 30, 25, 30
                        )
                )
        );

        // Question number
        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );
        questionNumberLabel.setForeground(
                new Color(45, 75, 160)
        );
        questionNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Question text
        questionLabel = new JLabel();
        questionLabel.setFont(
                new Font("Arial", Font.BOLD, 20)
        );
        questionLabel.setForeground(
                new Color(40, 40, 40)
        );
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionPanel.add(questionNumberLabel);
        questionPanel.add(Box.createVerticalStrut(15));
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(25));

        // ================= OPTIONS =================

        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionGroup = new ButtonGroup();

        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);
        
        JRadioButton[] options = {
                optionA, optionB, optionC, optionD
        };

        for (JRadioButton option : options) {

            option.setFont(
                    new Font("Arial", Font.PLAIN, 17)
            );

            option.setBackground(Color.WHITE);
            option.setForeground(
                    new Color(50, 50, 50)
            );

            option.setFocusPainted(false);
            option.setAlignmentX(Component.LEFT_ALIGNMENT);

            questionPanel.add(option);
            questionPanel.add(Box.createVerticalStrut(12));
        }

        mainPanel.add(questionPanel, BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(
                new Color(238, 243, 255)
        );

        // Navigation buttons
        JPanel navigationPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 5)
        );

        navigationPanel.setBackground(
                new Color(238, 243, 255)
        );

        previousButton = new JButton("← PREVIOUS");
        nextButton = new JButton("NEXT →");
        submitButton = new JButton("SUBMIT EXAM");

        // Previous
        previousButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        previousButton.setForeground(Color.WHITE);
        previousButton.setBackground(
                new Color(100, 120, 180)
        );
        previousButton.setFocusPainted(false);

        // Next
        nextButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(
                new Color(50, 130, 190)
        );
        nextButton.setFocusPainted(false);

        // Submit
        submitButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(
                new Color(210, 70, 70)
        );
        submitButton.setFocusPainted(false);

        navigationPanel.add(previousButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(submitButton);

        bottomPanel.add(navigationPanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // ================= BUTTON ACTIONS =================

        previousButton.addActionListener(e -> {

            saveCurrentAnswer();

            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion();
            }
        });

        nextButton.addActionListener(e -> {

            saveCurrentAnswer();

            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                loadQuestion();
            }
        });

        submitButton.addActionListener(e -> {
            saveCurrentAnswer();
            submitExam(false);
        });

        // First question load
        loadQuestion();

        revalidate();
        repaint();
    }

    private void submitExam(boolean autoSubmitted) {

        // Agar exam already submit ho chuka hai
        if (!examInProgress) {
            return;
        }

        // Current question ka answer save karo
        saveCurrentAnswer();

        // Manual submit ke liye confirmation
        if (!autoSubmitted) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to submit the exam?",
                    "Confirm Submission",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Timer stop
        stopExamTimer();

        // Exam khatam
        examInProgress = false;

        // Auto submit message
        if (autoSubmitted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Time is over. Your exam has been submitted automatically.",
                    "Time Up",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        // Directly Result Dashboard
        showResultScreen();
    }
    
    private void showResultScreen() {

        // ================= CALCULATE RESULT =================

        int total = questions.size();
        int correct = 0;
        int incorrect = 0;
        int unanswered = 0;

        for (int i = 0; i < total; i++) {

            if (selectedAnswers[i] == null ||
                selectedAnswers[i].trim().isEmpty()) {

                unanswered++;

            } else if (selectedAnswers[i].equals(
                    questions.get(i).getCorrectAnswer())) {

                correct++;

            } else {

                incorrect++;
            }
        }

        int score = correct;

        // Time taken
        long timeTakenSeconds = (30 * 60) - remainingSeconds;

        if (timeTakenSeconds < 0) {
            timeTakenSeconds = 0;
        }

        long minutes = timeTakenSeconds / 60;
        long seconds = timeTakenSeconds % 60;


        // ================= CLEAR SCREEN =================

        getContentPane().removeAll();


        // ================= MAIN PANEL =================

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBackground(
                new Color(238, 243, 255)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 30, 20, 30
                )
        );


        // ================= HEADER =================

        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setBackground(
                new Color(45, 75, 160)
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );


        JLabel titleLabel = new JLabel(
                "EXAM RESULT"
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(
                Color.WHITE
        );


        JLabel studentLabel = new JLabel(
                "Student: " +
                registeredUser.getDisplayName()
        );

        studentLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        studentLabel.setForeground(
                new Color(220, 230, 255)
        );


        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                studentLabel,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // ================= RESULT CARD =================

        JPanel resultCard = new JPanel();

        resultCard.setLayout(
                new BoxLayout(
                        resultCard,
                        BoxLayout.Y_AXIS
                )
        );

        resultCard.setBackground(
                Color.WHITE
        );

        resultCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(100, 130, 210),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 50, 30, 50
                        )
                )
        );


        // ================= SCORE =================

        JLabel scoreLabel = new JLabel(
                "YOUR SCORE: " +
                score +
                " / " +
                total
        );

        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        scoreLabel.setForeground(
                new Color(40, 150, 90)
        );

        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(scoreLabel);

        resultCard.add(
                Box.createVerticalStrut(25)
        );


        // ================= CORRECT =================

        JLabel correctLabel = new JLabel(
                "✓  Correct Answers: " +
                correct
        );

        correctLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        correctLabel.setForeground(
                new Color(40, 160, 90)
        );

        correctLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(correctLabel);

        resultCard.add(
                Box.createVerticalStrut(12)
        );


        // ================= INCORRECT =================

        JLabel incorrectLabel = new JLabel(
                "✗  Incorrect Answers: " +
                incorrect
        );

        incorrectLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        incorrectLabel.setForeground(
                new Color(210, 70, 70)
        );

        incorrectLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(incorrectLabel);

        resultCard.add(
                Box.createVerticalStrut(12)
        );


        // ================= UNANSWERED =================

        JLabel unansweredLabel = new JLabel(
                "○  Unanswered Questions: " +
                unanswered
        );

        unansweredLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        unansweredLabel.setForeground(
                new Color(220, 150, 40)
        );

        unansweredLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(unansweredLabel);

        resultCard.add(
                Box.createVerticalStrut(20)
        );


        // ================= TIME =================

        JLabel timeLabel = new JLabel(
                String.format(
                        "Time Taken: %02d:%02d",
                        minutes,
                        seconds
                )
        );

        timeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        timeLabel.setForeground(
                new Color(70, 90, 140)
        );

        timeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(timeLabel);

        resultCard.add(
                Box.createVerticalStrut(20)
        );


        // ================= PERFORMANCE MESSAGE =================

        JLabel messageLabel;

        if (score >= total * 0.7) {

            messageLabel = new JLabel(
                    "Excellent Performance!"
            );

            messageLabel.setForeground(
                    new Color(40, 150, 90)
            );

        } else if (score >= total * 0.4) {

            messageLabel = new JLabel(
                    "Good Job! Keep Improving!"
            );

            messageLabel.setForeground(
                    new Color(50, 110, 190)
            );

        } else {

            messageLabel = new JLabel(
                    "Keep Practicing! You Can Do Better!"
            );

            messageLabel.setForeground(
                    new Color(210, 100, 60)
            );
        }


        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        messageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        resultCard.add(messageLabel);


        mainPanel.add(
                resultCard,
                BorderLayout.CENTER
        );


        // ================= BOTTOM BUTTONS =================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        10
                )
        );

        bottomPanel.setBackground(
                new Color(238, 243, 255)
        );


        // Dashboard Button

        JButton dashboardButton = new JButton(
                "BACK TO DASHBOARD"
        );

        dashboardButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        dashboardButton.setForeground(
                Color.WHITE
        );

        dashboardButton.setBackground(
                new Color(50, 120, 190)
        );

        dashboardButton.setFocusPainted(false);


        // Logout Button

        JButton logoutButton = new JButton(
                "LOGOUT"
        );

        logoutButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        logoutButton.setForeground(
                Color.WHITE
        );

        logoutButton.setBackground(
                new Color(210, 70, 70)
        );

        logoutButton.setFocusPainted(false);


        bottomPanel.add(
                dashboardButton
        );

        bottomPanel.add(
                logoutButton
        );


        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ================= SHOW RESULT =================

        add(mainPanel);

        dashboardButton.addActionListener(e -> {
            showDashboard();
        });

        logoutButton.addActionListener(e -> {
            showLoginScreen();
        });


        revalidate();
        repaint();
    }
    
    
    private void showResultScreen(int score, int total, int correct,
            int incorrect, int unanswered,
            long timeTakenSeconds) {



}

   
    private void startExamTimer() {

        stopExamTimer();

        remainingSeconds = 30 * 60;

        timerLabel.setText("Time Remaining: 30:00");

        examTimer = new javax.swing.Timer(1000, e -> {

            remainingSeconds--;

            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            timerLabel.setText(
                    String.format(
                            "Time Remaining: %02d:%02d",
                            minutes,
                            seconds
                    )
            );

            if (remainingSeconds <= 0) {

                examTimer.stop();

                submitExam(true);
            }
        });

        examTimer.start();
    }
    private void stopExamTimer() {

        if (examTimer != null) {
            examTimer.stop();
        }
    }
    

    private void loadQuestion() {
        Question q = questions.get(currentQuestionIndex);
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        questionLabel.setText("<html>" + q.getQuestionText() + "</html>");
        optionA.setText("A. " + q.getOptionA());
        optionB.setText("B. " + q.getOptionB());
        optionC.setText("C. " + q.getOptionC());
        optionD.setText("D. " + q.getOptionD());
        optionGroup.clearSelection();

        String answer = selectedAnswers[currentQuestionIndex];
        if (answer != null) {
            if (answer.equals("A")) optionA.setSelected(true);
            else if (answer.equals("B")) optionB.setSelected(true);
            else if (answer.equals("C")) optionC.setSelected(true);
            else if (answer.equals("D")) optionD.setSelected(true);
        }
    }

    private void saveCurrentAnswer() {
        if (optionA != null && optionA.isSelected()) selectedAnswers[currentQuestionIndex] = "A";
        else if (optionB != null && optionB.isSelected()) selectedAnswers[currentQuestionIndex] = "B";
        else if (optionC != null && optionC.isSelected()) selectedAnswers[currentQuestionIndex] = "C";
        else if (optionD != null && optionD.isSelected()) selectedAnswers[currentQuestionIndex] = "D";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineExamSystem examSystem = new OnlineExamSystem();
            examSystem.setVisible(true);
        });
    }
}
