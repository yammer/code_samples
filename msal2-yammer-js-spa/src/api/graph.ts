const graphApiBase = 'https://graph.microsoft.com/beta/';

export const callGraphApi = async (accessToken: string, endpoint: string) => {
  const request = new Request(`${graphApiBase}${endpoint}`, {
    headers: new Headers({
      'Authorization': `Bearer ${accessToken}`,
    }),
  });

  const response = await fetch(request);
  return await response.json();
};
