export class Account {
  constructor(
    public activated: boolean,
    public perfis: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string
  ) {}
}
