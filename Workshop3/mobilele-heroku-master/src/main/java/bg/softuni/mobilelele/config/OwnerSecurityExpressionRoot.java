package bg.softuni.mobilelele.config;

import bg.softuni.mobilelele.service.OfferService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements
    MethodSecurityExpressionOperations {

  private OfferService offerService;
  private Object filterObject;
  private Object returnObject;

  /**
   * Creates a new instance
   *
   * @param authentication the {@link Authentication} to use. Cannot be null.
   */
  public OwnerSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  public boolean isOwner(Long id) {
    String userName = currentUserName();
    if (userName != null) {
      return offerService.isOwner(userName, id);
    }
    return false;
  }


  public void setOfferService(OfferService offerService) {
    this.offerService = offerService;
  }

  public String currentUserName() {
    Authentication auth = getAuthentication();
    if (auth.getPrincipal() instanceof UserDetails) {
      return ((UserDetails)auth.getPrincipal()).getUsername();
    }
    return null;
  }

  @Override
  public void setFilterObject(Object filterObject) {
    this.filterObject = filterObject;
  }

  @Override
  public Object getFilterObject() {
    return filterObject;
  }

  @Override
  public void setReturnObject(Object returnObject) {
    this.returnObject = returnObject;
  }

  @Override
  public Object getReturnObject() {
    return returnObject;
  }

  @Override
  public Object getThis() {
    return this;
  }
}
