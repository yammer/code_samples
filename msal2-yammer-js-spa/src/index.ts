import { Authenticator } from './authenticator';
import { renderApp, renderSignIn } from './ui';

(async () => {
  const authenticator = new Authenticator();

  await authenticator.handleRedirect();

  if (authenticator.hasAccount()) {
    renderApp(authenticator);
  } else {
    renderSignIn(authenticator);
  }
})();
