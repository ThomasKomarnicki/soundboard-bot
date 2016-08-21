SELECT 
  guild_opts.guild_id, guild_opts._id, guild_opts.privileged_roles, soundboards._id as soundboard_id,
  soundboards.name as soundboard_name, sound_clips.clip_url, sound_clips._id as sound_clip_id, 
  sound_clips.name as sound_clip_name
FROM 
  guild_opts.guild_opts LEFT JOIN guild_opts.soundboards ON (guild_opts._id = soundboards.guild_opts_id)
  LEFT JOIN guild_opts.sound_clips ON (sound_clips.soundboard_id = soundboards._id)
WHERE
  guild_opts.guild_id = 'test_guild_id_2';

