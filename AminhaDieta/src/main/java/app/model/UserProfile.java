package app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id = UUID.randomUUID();

    private String nome;
    private int idade;
    private double pesoKg;
    private double alturaCm;
    private Gender gender; // Adicionado género

    public enum Gender {
        MALE("Masculino"), FEMALE("Feminino");

        private String label;

        Gender(String label) {
            this.label = label;
        }

        public String toString() {
            return label;
        }
    }

    // dados específicos deste perfil
    private final List<MealEntry> meals = new ArrayList<>();
    private final List<WaterEntry> waters = new ArrayList<>();
    private final List<WeightEntry> weights = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();

    public UserProfile(String nome, int idade, double pesoKg, double alturaCm, Gender gender) {
        this.nome = nome;
        this.idade = idade;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
        this.gender = gender;
    }

    // Calculators
    public double getBMI() {
        if (alturaCm <= 0)
            return 0;
        double heightM = alturaCm / 100.0;
        return pesoKg / (heightM * heightM);
    }

    public int getDailyCalorieGoal() {
        // Mifflin-St Jeor Equation
        // Sedentary factor 1.2 assumed for now
        double bmr = (10 * pesoKg) + (6.25 * alturaCm) - (5 * idade);
        if (gender == Gender.MALE)
            bmr += 5;
        else
            bmr -= 161;

        return (int) (bmr * 1.2);
    }

    public double getDailyWaterGoalMl() {
        return 35 * pesoKg;
    }

    // Progress
    public int getCaloriesConsumedToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return meals.stream()
                .filter(m -> m.getTimestamp().toLocalDate().equals(today))
                .mapToInt(MealEntry::getCalories)
                .sum();
    }

    public double getProteinConsumedToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return meals.stream()
                .filter(m -> m.getTimestamp().toLocalDate().equals(today))
                .mapToDouble(MealEntry::getProtein)
                .sum();
    }

    public double getCarbsConsumedToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return meals.stream()
                .filter(m -> m.getTimestamp().toLocalDate().equals(today))
                .mapToDouble(MealEntry::getCarbs)
                .sum();
    }

    public double getFatConsumedToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return meals.stream()
                .filter(m -> m.getTimestamp().toLocalDate().equals(today))
                .mapToDouble(MealEntry::getFat)
                .sum();
    }

    // Macro Targets (50% Carbs, 30% Fat, 20% Protein)
    // Carbs/Protein = 4kcal/g, Fat = 9kcal/g
    public double getDailyProteinGoalGrams() {
        return (getDailyCalorieGoal() * 0.20) / 4.0;
    }

    public double getDailyCarbsGoalGrams() {
        return (getDailyCalorieGoal() * 0.50) / 4.0;
    }

    public double getDailyFatGoalGrams() {
        return (getDailyCalorieGoal() * 0.30) / 9.0;
    }

    public double getWaterConsumedToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return waters.stream()
                .filter(w -> w.getTimestamp().toLocalDate().equals(today))
                .mapToDouble(WaterEntry::getAmountMl)
                .sum();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public double getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(double alturaCm) {
        this.alturaCm = alturaCm;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<MealEntry> getMeals() {
        return meals;
    }

    public List<WaterEntry> getWaters() {
        return waters;
    }

    public List<WeightEntry> getWeights() {
        return weights;
    }

    public List<Food> getFoods() {
        return foods;
    }
}
