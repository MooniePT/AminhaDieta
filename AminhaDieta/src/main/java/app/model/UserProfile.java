package app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa o perfil de um utilizador, contendo dados pessoais, histórico de
 * refeições, exercícios, peso e água.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id = UUID.randomUUID();

    private String nome;
    private int idade;
    private double pesoKg;
    private double alturaCm;
    private Gender gender; // Género do utilizador
    private PhysicalActivityLevel physicalActivityLevel; // Nível de atividade física

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

    public enum PhysicalActivityLevel {
        SEDENTARY("Sedentário", 1.2),
        LIGHTLY_ACTIVE("Levemente Ativo", 1.375),
        MODERATELY_ACTIVE("Moderadamente Ativo", 1.55),
        VERY_ACTIVE("Muito Ativo", 1.725),
        EXTRA_ACTIVE("Extremamente Ativo", 1.9);

        private final String label;
        private final double multiplier;

        PhysicalActivityLevel(String label, double multiplier) {
            this.label = label;
            this.multiplier = multiplier;
        }

        public double getMultiplier() {
            return multiplier;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // Dados específicos deste perfil (Listas de registos)
    private final List<MealEntry> meals = new ArrayList<>();
    private final List<WaterEntry> waters = new ArrayList<>();
    private final List<WeightEntry> weights = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private List<ExerciseEntry> exercises = new ArrayList<>();

    public UserProfile(String nome, int idade, double pesoKg, double alturaCm, Gender gender,
            PhysicalActivityLevel physicalActivityLevel) {
        this.nome = nome;
        this.idade = idade;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
        this.gender = gender;
        this.physicalActivityLevel = physicalActivityLevel;
    }

    // Calculadoras e Métodos Utilitários
    public double getBMI() {
        if (alturaCm <= 0)
            return 0;
        double heightM = alturaCm / 100.0;
        return pesoKg / (heightM * heightM);
    }

    public int getDailyCalorieGoal() {
        // Equação de Mifflin-St Jeor
        // Fator base assumido se não houver nível de atividade definido
        double bmr = (10 * pesoKg) + (6.25 * alturaCm) - (5 * idade);
        if (gender == Gender.MALE)
            bmr += 5;
        else
            bmr -= 161;

        double multiplier = (physicalActivityLevel != null) ? physicalActivityLevel.getMultiplier() : 1.2;
        return (int) (bmr * multiplier);
    }

    public double getDailyWaterGoalMl() {
        return 35 * pesoKg;
    }

    // Progresso Diário
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

    // Metas de Macros (50% Hidratos, 30% Gordura, 20% Proteína)
    // Hidratos/Proteína = 4kcal/g, Gordura = 9kcal/g
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

    public PhysicalActivityLevel getPhysicalActivityLevel() {
        return physicalActivityLevel;
    }

    public void setPhysicalActivityLevel(PhysicalActivityLevel physicalActivityLevel) {
        this.physicalActivityLevel = physicalActivityLevel;
    }

    public List<ExerciseEntry> getExercises() {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        return exercises;
    }

    public List<MealEntry> getMeals() {
        if (meals == null)
            return new ArrayList<>();
        return meals;
    }

    public List<WaterEntry> getWaters() {
        if (waters == null)
            return new ArrayList<>();
        return waters;
    }

    public List<WeightEntry> getWeights() {
        if (weights == null)
            return new ArrayList<>();
        return weights;
    }

    public List<Food> getFoods() {
        if (foods == null)
            return new ArrayList<>();
        return foods;
    }
}
