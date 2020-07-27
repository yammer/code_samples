const yammerApiBase = 'https://api.yammer.com/';

export const callYammerApi = async (accessToken: string, endpoint: string) => {
  const request = new Request(`${yammerApiBase}${endpoint}`, {
    headers: new Headers({
      'Authorization': `Bearer ${accessToken}`,
    }),
  });

  const response = await fetch(request);
  return await response.json();
};
