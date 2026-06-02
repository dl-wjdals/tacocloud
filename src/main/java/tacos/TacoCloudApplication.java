package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tacos.data.IngredientRepository;

@SpringBootApplication
public class TacoCloudApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        System.out.println("Taco Cloud application started");
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo) {
        return args -> {
            if (ingredientRepo.count() == 0) {
                ingredientRepo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                ingredientRepo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                ingredientRepo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                ingredientRepo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                ingredientRepo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                ingredientRepo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                ingredientRepo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                ingredientRepo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                ingredientRepo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                ingredientRepo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
            }
        };
    }
}
