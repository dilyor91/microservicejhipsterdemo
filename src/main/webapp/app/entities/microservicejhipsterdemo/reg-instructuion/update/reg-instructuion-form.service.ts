import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRegInstructuion, NewRegInstructuion } from '../reg-instructuion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegInstructuion for edit and NewRegInstructuionFormGroupInput for create.
 */
type RegInstructuionFormGroupInput = IRegInstructuion | PartialWithRequiredKeyOf<NewRegInstructuion>;

type RegInstructuionFormDefaults = Pick<NewRegInstructuion, 'id' | 'status'>;

type RegInstructuionFormGroupContent = {
  id: FormControl<IRegInstructuion['id'] | NewRegInstructuion['id']>;
  titleUz: FormControl<IRegInstructuion['titleUz']>;
  titleRu: FormControl<IRegInstructuion['titleRu']>;
  titleKr: FormControl<IRegInstructuion['titleKr']>;
  contentUz: FormControl<IRegInstructuion['contentUz']>;
  contentRu: FormControl<IRegInstructuion['contentRu']>;
  contentKr: FormControl<IRegInstructuion['contentKr']>;
  status: FormControl<IRegInstructuion['status']>;
};

export type RegInstructuionFormGroup = FormGroup<RegInstructuionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegInstructuionFormService {
  createRegInstructuionFormGroup(regInstructuion: RegInstructuionFormGroupInput = { id: null }): RegInstructuionFormGroup {
    const regInstructuionRawValue = {
      ...this.getFormDefaults(),
      ...regInstructuion,
    };
    return new FormGroup<RegInstructuionFormGroupContent>({
      id: new FormControl(
        { value: regInstructuionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleUz: new FormControl(regInstructuionRawValue.titleUz, {
        validators: [Validators.required],
      }),
      titleRu: new FormControl(regInstructuionRawValue.titleRu, {
        validators: [Validators.required],
      }),
      titleKr: new FormControl(regInstructuionRawValue.titleKr, {
        validators: [Validators.required],
      }),
      contentUz: new FormControl(regInstructuionRawValue.contentUz, {
        validators: [Validators.required],
      }),
      contentRu: new FormControl(regInstructuionRawValue.contentRu, {
        validators: [Validators.required],
      }),
      contentKr: new FormControl(regInstructuionRawValue.contentKr, {
        validators: [Validators.required],
      }),
      status: new FormControl(regInstructuionRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getRegInstructuion(form: RegInstructuionFormGroup): IRegInstructuion | NewRegInstructuion {
    return form.getRawValue() as IRegInstructuion | NewRegInstructuion;
  }

  resetForm(form: RegInstructuionFormGroup, regInstructuion: RegInstructuionFormGroupInput): void {
    const regInstructuionRawValue = { ...this.getFormDefaults(), ...regInstructuion };
    form.reset(
      {
        ...regInstructuionRawValue,
        id: { value: regInstructuionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RegInstructuionFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
