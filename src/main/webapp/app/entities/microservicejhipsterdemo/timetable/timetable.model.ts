export interface ITimetable {
  id: number;
  titleUz?: string | null;
  titleRu?: string | null;
  titleKr?: string | null;
  contentUz?: string | null;
  contentRu?: string | null;
  contentKr?: string | null;
  status?: boolean | null;
}

export type NewTimetable = Omit<ITimetable, 'id'> & { id: null };
