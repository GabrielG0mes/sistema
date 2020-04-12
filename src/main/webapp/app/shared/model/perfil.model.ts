import { IOperador } from 'app/shared/model/operador.model';

export interface IPerfil {
  id?: number;
  nome?: string;
  operadors?: IOperador[];
}

export class Perfil implements IPerfil {
  constructor(public id?: number, public nome?: string, public operadors?: IOperador[]) {}
}
