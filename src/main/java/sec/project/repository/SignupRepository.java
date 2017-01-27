package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sec.project.domain.Signup;

@RepositoryRestResource
public interface SignupRepository extends JpaRepository<Signup, Long> {

}
