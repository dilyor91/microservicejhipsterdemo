import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAddmissionRules, NewAddmissionRules } from '../addmission-rules.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddmissionRules for edit and NewAddmissionRulesFormGroupInput for create.
 */
type AddmissionRulesFormGroupInput = IAddmissionRules | PartialWithRequiredKeyOf<NewAddmissionRules>;

type AddmissionRulesFormDefaults = Pick<NewAddmissionRules, 'id' | 'status'>;

type AddmissionRulesFormGroupContent = {
  id: FormControl<IAddmissionRules['id'] | NewAddmissionRules['id']>;
  titleUz: FormControl<IAddmissionRules['titleUz']>;
  titleRu: FormControl<IAddmissionRules['titleRu']>;
  titleKr: FormControl<IAddmissionRules['titleKr']>;
  contentUz: FormControl<IAddmissionRules['contentUz']>;
  contentRu: FormControl<IAddmissionRules['contentRu']>;
  contentKr: FormControl<IAddmissionRules['contentKr']>;
  status: FormControl<IAddmissionRules['status']>;
};

export type AddmissionRulesFormGroup = FormGroup<AddmissionRulesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddmissionRulesFormService {
  createAddmissionRulesFormGroup(addmissionRules: AddmissionRulesFormGroupInput = { id: null }): AddmissionRulesFormGroup {
    const addmissionRulesRawValue = {
      ...this.getFormDefaults(),
      ...addmissionRules,
    };
    return new FormGroup<AddmissionRulesFormGroupContent>({
      id: new FormControl(
        { value: addmissionRulesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleUz: new FormControl(addmissionRulesRawValue.titleUz, {
        validators: [Validators.required],
      }),
      titleRu: new FormControl(addmissionRulesRawValue.titleRu, {
        validators: [Validators.required],
      }),
      titleKr: new FormControl(addmissionRulesRawValue.titleKr, {
        validators: [Validators.required],
      }),
      contentUz: new FormControl(addmissionRulesRawValue.contentUz, {
        validators: [Validators.required],
      }),
      contentRu: new FormControl(addmissionRulesRawValue.contentRu, {
        validators: [Validators.required],
      }),
      contentKr: new FormControl(addmissionRulesRawValue.contentKr, {
        validators: [Validators.required],
      }),
      status: new FormControl(addmissionRulesRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getAddmissionRules(form: AddmissionRulesFormGroup): IAddmissionRules | NewAddmissionRules {
    return form.getRawValue() as IAddmissionRules | NewAddmissionRules;
  }

  resetForm(form: AddmissionRulesFormGroup, addmissionRules: AddmissionRulesFormGroupInput): void {
    const addmissionRulesRawValue = { ...this.getFormDefaults(), ...addmissionRules };
    form.reset(
      {
        ...addmissionRulesRawValue,
        id: { value: addmissionRulesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddmissionRulesFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
