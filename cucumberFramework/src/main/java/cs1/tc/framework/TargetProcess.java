package cs1.tc.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TargetProcess {
	public String tpLink() default "https://cybersponse.tpondemand.com/RestUI/Board.aspx?"
								  +"acid=5D6AC326B4D272C23E446C28586BAF51#page=testcase/";
	public String tcNumber() default "";
}
