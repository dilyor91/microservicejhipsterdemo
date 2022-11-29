import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITimetable, NewTimetable } from '../timetable.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITimetable for edit and NewTimetableFormGroupInput for create.
 */
type TimetableFormGroupInput = ITimetable | PartialWithRequiredKeyOf<NewTimetable>;

type TimetableFormDefaults = Pick<NewTimetable, 'id' | 'status'>;

type TimetableFormGroupContent = {
  id: FormControl<ITimetable['id'] | NewTimetable['id']>;
  titleUz: FormControl<ITimetable['titleUz']>;
  titleRu: FormControl<ITimetable['titleRu']>;
  titleKr: FormControl<ITimetable['titleKr']>;
  contentUz: FormControl<ITimetable['contentUz']>;
  contentRu: FormControl<ITimetable['contentRu']>;
  contentKr: FormControl<ITimetable['contentKr']>;
  status: FormControl<ITimetable['status']>;
};

export type TimetableFormGroup = FormGroup<TimetableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TimetableFormService {
  createTimetableFormGroup(timetable: TimetableFormGroupInput = { id: null }): TimetableFormGroup {
    const timetableRawValue = {
      ...this.getFormDefaults(),
      ...timetable,
    };
    return new FormGroup<TimetableFormGroupContent>({
      id: new FormControl(
        { value: timetableRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titleUz: new FormControl(timetableRawValue.titleUz, {
        validators: [Validators.required],
      }),
      titleRu: new FormControl(timetableRawValue.titleRu, {
        validators: [Validators.required],
      }),
      titleKr: new FormControl(timetableRawValue.titleKr, {
        validators: [Validators.required],
      }),
      contentUz: new FormControl(timetableRawValue.contentUz, {
        validators: [Validators.required],
      }),
      contentRu: new FormControl(timetableRawValue.contentRu, {
        validators: [Validators.required],
      }),
      contentKr: new FormControl(timetableRawValue.contentKr, {
        validators: [Validators.required],
      }),
      status: new FormControl(timetableRawValue.status, {
        validators: [Validators.required],
      }),
    });
  }

  getTimetable(form: TimetableFormGroup): ITimetable | NewTimetable {
    return form.getRawValue() as ITimetable | NewTimetable;
  }

  resetForm(form: TimetableFormGroup, timetable: TimetableFormGroupInput): void {
    const timetableRawValue = { ...this.getFormDefaults(), ...timetable };
    form.reset(
      {
        ...timetableRawValue,
        id: { value: timetableRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TimetableFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
