SELECT 
  * 
FROM 
  guild_opts.guild_opts, guild_opts.soundboards, guild_opts.sound_clips
  WHERE soundboards.guild_opts_id = guild_opts._id AND guild_opts.guild_id = 'test_guild_id_2';
