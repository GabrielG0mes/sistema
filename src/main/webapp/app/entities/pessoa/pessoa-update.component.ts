import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPessoa, Pessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from './pessoa.service';

@Component({
  selector: 'jhi-pessoa-update',
  templateUrl: './pessoa-update.component.html'
})
export class PessoaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.pattern('^[A-Za-z- ]*')]],
    documento: ['', [Validators.required, Validators.pattern('^[0-9]{11}')]],
    dataNascimento: ['', [this.validarData()]],
    nomeMae: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.pattern('^[A-Za-z- ]*')]],
    nomePai: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.pattern('^[A-Za-z- ]*')]],
    tipo: ['', [Validators.required]]
  });

  constructor(protected pessoaService: PessoaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  validarData(): any {
    return (control: FormControl): { [key: string]: any } => {
      const data = new Date(control.value);
      const hoje = new Date();
      if (data > hoje) return { invalidDate: true };
      return {};
    };
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      if (!pessoa.id) {
        const today = moment().startOf('day');
        pessoa.dataNascimento = today;
        pessoa.dataCadastro = today;
      }

      this.updateForm(pessoa);
    });
  }

  updateForm(pessoa: IPessoa): void {
    this.editForm.patchValue({
      id: pessoa.id,
      nome: pessoa.nome,
      documento: pessoa.documento,
      dataNascimento: pessoa.dataNascimento ? pessoa.dataNascimento.format(DATE_TIME_FORMAT) : null,
      nomeMae: pessoa.nomeMae,
      nomePai: pessoa.nomePai,
      tipo: pessoa.tipo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoa = this.createFromForm();
    if (pessoa.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaService.update(pessoa));
    } else {
      this.subscribeToSaveResponse(this.pessoaService.create(pessoa));
    }
  }

  private createFromForm(): IPessoa {
    return {
      ...new Pessoa(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      documento: this.editForm.get(['documento'])!.value,
      dataNascimento: this.editForm.get(['dataNascimento'])!.value
        ? moment(this.editForm.get(['dataNascimento'])!.value, DATE_TIME_FORMAT)
        : undefined,
      nomeMae: this.editForm.get(['nomeMae'])!.value,
      nomePai: this.editForm.get(['nomePai'])!.value,
      tipo: this.editForm.get(['tipo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoa>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
