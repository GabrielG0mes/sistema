import { Moment } from 'moment';
import { IPerfil } from 'app/shared/model/perfil.model';

export interface IOperador {
  id?: number;
  nome?: string;
  login?: string;
  senha?: string;
  dataCadastro?: Moment;
  perfils?: IPerfil[];
}

export class Operador implements IOperador {
  constructor(
    public id?: number,
    public nome?: string,
    public login?: string,
    public senha?: string,
    public dataCadastro?: Moment,
    public perfils?: IPerfil[]
  ) {}
}
