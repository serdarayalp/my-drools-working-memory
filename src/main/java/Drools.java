import de.mydomain.model.Product;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Drools {
    public void executeDrools() {
        try {
            PackageBuilder packageBuilder = new PackageBuilder();

            String ruleFile = "com/rule/rules.drl";
            InputStream rule = getClass().getClassLoader().getResourceAsStream(ruleFile);

            if (rule != null) {
                Reader reader = new InputStreamReader(rule);
                packageBuilder.addPackageFromDrl(reader);
                org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
                RuleBase ruleBase = RuleBaseFactory.newRuleBase();
                ruleBase.addPackage(rulesPackage);

                WorkingMemory workingMemory = ruleBase.newStatefulSession();

                Product product = new Product();
                product.setType("gold");

                workingMemory.insert(product);
                workingMemory.fireAllRules();

                System.out.println("The discount for the product " + product.getType() + " is " + product.getDiscount());
            }
        } catch (DroolsParserException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
