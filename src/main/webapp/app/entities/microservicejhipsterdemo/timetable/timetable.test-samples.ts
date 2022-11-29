import { ITimetable, NewTimetable } from './timetable.model';

export const sampleWithRequiredData: ITimetable = {
  id: 25211,
  titleUz: 'Senior',
  titleRu: 'discrete',
  titleKr: 'cross-platform Directives',
  contentUz: 'Generic revolutionize Supervisor',
  contentRu: 'projection Sharable virtual',
  contentKr: 'vortals Intelligent',
  status: false,
};

export const sampleWithPartialData: ITimetable = {
  id: 31474,
  titleUz: 'Personal Visionary',
  titleRu: 'Hat Cambridgeshire',
  titleKr: 'e-enable virtual',
  contentUz: 'Pitcairn deposit Mexican',
  contentRu: 'Configurable Corporate',
  contentKr: 'Generic',
  status: true,
};

export const sampleWithFullData: ITimetable = {
  id: 81444,
  titleUz: 'bandwidth',
  titleRu: 'invoice Refined back-end',
  titleKr: 'task-force',
  contentUz: 'middleware',
  contentRu: 'Street Intelligent auxiliary',
  contentKr: 'AI',
  status: false,
};

export const sampleWithNewData: NewTimetable = {
  titleUz: 'Buckinghamshire',
  titleRu: 'uniform',
  titleKr: 'hard Director',
  contentUz: 'parse Ergonomic Islands',
  contentRu: 'withdrawal portal Connecticut',
  contentKr: 'green deposit',
  status: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
