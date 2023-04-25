export PATH=$PATH:$HOME/minio-binaries/

if [ ! -d "$HOME/minio-binaries/mc" ]; then
  echo "Minio client was not found, downloading it"

  curl https://dl.min.io/client/mc/release/linux-amd64/mc \
    --create-dirs \
    -o $HOME/minio-binaries/mc

  chmod +x $HOME/minio-binaries/mc

  mc alias set local_minio http://ol.local:9010 root rootroot
fi

backup_filename="$(date +%s)".sql

docker exec -it pg_db pg_dump vardb > ~/jp/db_backup/"$backup_filename"

mc cp ~/jp/db_backup/"$backup_filename" local_minio/vardb-backup/"$backup_filename"

echo "Database backup was successful"
