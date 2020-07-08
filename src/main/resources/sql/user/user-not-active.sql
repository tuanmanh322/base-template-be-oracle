SELECT
  U.ID id,
  U.USERNAME username,
  U.EMAIL email,
  U.LANG_KEY langKey,
  U.IMAGE_URL imageUrl,
  U.CREATED_DATE createDate,
  U.UPDATED_DATE updateDate
FROM TBL_USER U
WHERE U.ACTIVATED = :p_status